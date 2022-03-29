package org.byteskript.bytecode.syntax.constant;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class Const extends Effect {
    
    public Const() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT,
            "aconst_null",
            "iconst_m1",
            "iconst_0",
            "iconst_1",
            "iconst_2",
            "iconst_3",
            "iconst_4",
            "iconst_5",
            "lconst_0",
            "lconst_1",
            "fconst_0",
            "fconst_1",
            "fconst_2",
            "dconst_0",
            "dconst_1"
        );
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.contains("const")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode;
        if (match.matchedPattern > 0 && match.matchedPattern < 16) opcode = match.matchedPattern + 1;
        else opcode = 1;
        method.writeCode((writer, visitor) -> visitor.visitInsn(opcode));
        context.setState(CompileState.CODE_BODY);
    }
    
}
