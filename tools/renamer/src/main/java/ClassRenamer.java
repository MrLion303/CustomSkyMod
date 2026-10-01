import org.objectweb.asm.*;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import java.nio.file.*;

public class ClassRenamer {
  static final String OLD="org/cotii/customskyboxes";
  static final String NEW="org/cotii/customskymod";

  static String renameText(String s) {
    return s.replace("customskyboxes","customskymod")
      .replace("CustomSkyboxes","CustomSkyMod")
      .replace("Customskyboxes","CustomSkyMod")
      .replace("org.cotii.customskyboxes","org.cotii.customskymod");
  }

  public static void main(String[] args) throws Exception {
    Path in=Path.of(args[0]), out=Path.of(args[1]);
    boolean skipForge=args.length > 2 && Boolean.parseBoolean(args[2]);
    Files.createDirectories(out);

    try (var stream=Files.walk(in)) {
      stream.filter(p->p.toString().endsWith(".class"))
        .filter(p->!skipForge || !p.toString().contains("/forge/"))
        .forEach(p->{
          try {
            ClassReader cr=new ClassReader(Files.readAllBytes(p));
            ClassWriter cw=new ClassWriter(0);
            Remapper rem=new Remapper() {
              @Override public String map(String n) {
                return n==null ? n : n.replace(OLD,NEW);
              }
            };
            ClassVisitor visitor=new ClassRemapper(cw,rem) {
              @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                AnnotationVisitor av=super.visitAnnotation(descriptor,visible);
                return new AnnotationVisitor(Opcodes.ASM9,av) {
                  @Override public void visit(String name,Object value) {
                    if(value instanceof String s) value=renameText(s);
                    super.visit(name,value);
                  }
                };
              }
              @Override public MethodVisitor visitMethod(int access,String name,String descriptor,String signature,String[] exceptions) {
                MethodVisitor mv=super.visitMethod(access,name,descriptor,signature,exceptions);
                return new MethodVisitor(Opcodes.ASM9,mv) {
                  @Override public void visitLdcInsn(Object c) {
                    if(c instanceof String s) c=renameText(s);
                    super.visitLdcInsn(c);
                  }
                };
              }
            };
            cr.accept(visitor,0);
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
