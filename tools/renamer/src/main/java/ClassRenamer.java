import org.objectweb.asm.*;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import java.nio.file.*;

public class ClassRenamer {
  static final String OLD="org/cotii/customskyboxes";
  static final String NEW="org/cotii/customskymod";

  public static void main(String[] args) throws Exception {
    Path in=Path.of(args[0]), out=Path.of(args[1]);
    boolean skipForge=args.length > 2 && Boolean.parseBoolean(args[2]);
    Files.createDirectories(out);

    try (var stream=Files.walk(in)) {
      stream.filter(p->p.toString().endsWith(".class"))
        .filter(p->!skipForge || !p.toString().contains("/forge/"))
        .forEach(p->{
          try {
            byte[] data=Files.readAllBytes(p);
            ClassReader cr=new ClassReader(data);
            ClassWriter cw=new ClassWriter(0);
            Remapper rem=new Remapper() {
              @Override public String map(String n) {
                return n==null ? n : n.replace(OLD,NEW);
              }
            };
            cr.accept(new ClassRemapper(cw,rem) {
              @Override public void visitLdcInsn(Object c) {
                if (c instanceof String s) {
                  c=s.replace("customskyboxes","customskymod")
                      .replace("CustomSkyboxes","CustomSkyMod")
                      .replace("Customskyboxes","CustomSkyMod");
                }
                super.visitLdcInsn(c);
              }
            },0);
            byte[] result=cw.toByteArray();
            String name=new ClassReader(result).getClassName();
            Path q=out.resolve(name+".class");
            Files.createDirectories(q.getParent());
            Files.write(q,result);
          } catch(Exception e) {
            throw new RuntimeException(e);
          }
        });
    }
  }
}
