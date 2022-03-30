package org.byteskript.bytecode.syntax.jump;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.error.ScriptCompileError;
import org.byteskript.skript.lang.element.StandardElements;
import org.objectweb.asm.Label;

public class IfSmallComparison extends Effect {
    
    public IfSmallComparison() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "if_icmpeq %Label%", "if_icmpne %Label%", "if_icmplt %Label%", "if_icmpge %Label%", "if_icmpgt %Label%", "if_icmple %Label%");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.startsWith("if_i")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void preCompile(Context context, Pattern.Match match) throws Throwable {
        context.getCompileCurrent().disableChildren();
        super.preCompile(context, match);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final Integer value = context.getCompileCurrent().nested()[0].getLiteralValue();
        if (value == null) throw new ScriptCompileError(context.lineNumber(), "No label index provided.");
        final LabelTree tree;
        {
            final LabelTree found = context.findTree(LabelTree.class);
            if (found != null) tree = found;
            else context.createTree(tree = new LabelTree(context.getTriggerSection()));
        }
        final Label label = tree.getLabel(value);
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = match.matchedPattern + 159;
        assert opcode >= 159 && opcode <= 164;
        method.writeCode((writer, visitor) -> visitor.visitJumpInsn(opcode, label));
        context.setState(CompileState.CODE_BODY);
    }
    
}
