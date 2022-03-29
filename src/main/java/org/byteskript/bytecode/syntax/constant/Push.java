package org.byteskript.bytecode.syntax.constant;

import mx.kenzie.foundation.MethodBuilder;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;

public class Push extends Effect {
    
    public Push() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "bipush %Integer%", "sipush %Integer%");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.contains("push ")) return null;
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
        final MethodBuilder method = context.getMethod();
        assert method != null;
        final int opcode = match.matchedPattern + 16;
        if (value == null) method.writeCode((writer, visitor) -> visitor.visitIntInsn(opcode, 0));
        else method.writeCode((writer, visitor) -> visitor.visitIntInsn(opcode, value));
        context.setState(CompileState.CODE_BODY);
    }
    
}
