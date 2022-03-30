package org.byteskript.bytecode.syntax.jump;

import mx.kenzie.foundation.MethodBuilder;
import mx.kenzie.foundation.compiler.State;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.skript.api.syntax.Literal;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.error.ScriptCompileError;
import org.byteskript.skript.lang.element.StandardElements;
import org.objectweb.asm.Label;

public class LabelEffect extends Literal<Integer> {
    
    public LabelEffect() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "l%Integer%");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.startsWith("l")) return null;
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
        method.writeCode((writer, visitor) -> visitor.visitLabel(label));
        context.setState(CompileState.CODE_BODY);
        
    }
    
    public boolean allowedIn(State state, Context context) {
        return state == CompileState.STATEMENT || state == CompileState.CODE_BODY;
    }
    
    @Override
    public Integer parse(String string) {
        return Integer.valueOf(string.substring(1));
    }
    
}
