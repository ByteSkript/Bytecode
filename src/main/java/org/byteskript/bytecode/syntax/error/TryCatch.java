package org.byteskript.bytecode.syntax.error;

import mx.kenzie.foundation.MethodBuilder;
import mx.kenzie.foundation.Type;
import org.byteskript.bytecode.Bytecode;
import org.byteskript.bytecode.syntax.jump.LabelTree;
import org.byteskript.skript.api.syntax.Effect;
import org.byteskript.skript.compiler.CompileState;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.ElementTree;
import org.byteskript.skript.compiler.Pattern;
import org.byteskript.skript.lang.element.StandardElements;
import org.objectweb.asm.Label;

public class TryCatch extends Effect {
    
    public TryCatch() {
        super(Bytecode.LIBRARY, StandardElements.EFFECT, "trycatchblock %Label%, %Label%, %Label%, %Type%");
    }
    
    @Override
    public Pattern.Match match(String thing, Context context) {
        if (!thing.startsWith("trycatchblock")) return null;
        return super.match(thing, context);
    }
    
    @Override
    public void preCompile(Context context, Pattern.Match match) throws Throwable {
        context.getCompileCurrent().disableChildren();
        super.preCompile(context, match);
    }
    
    @Override
    public void compile(Context context, Pattern.Match match) {
        final ElementTree[] nested = context.getCompileCurrent().nested();
        final Integer[] values = new Integer[3];
        values[0] = nested[0].getLiteralValue();
        values[1] = nested[1].getLiteralValue();
        values[2] = nested[2].getLiteralValue();
        final Type type = nested[3].getLiteralValue();
        final LabelTree tree;
        {
            final LabelTree found = context.findTree(LabelTree.class);
            if (found != null) tree = found;
            else context.createTree(tree = new LabelTree(context.getTriggerSection()));
        }
        final Label a = tree.getLabel(values[0]), b = tree.getLabel(values[1]), c = tree.getLabel(values[2]);
        final String name = type != null ? type.internalName() : null;
        final MethodBuilder method = context.getMethod();
        assert method != null;
        method.writeCode((writer, visitor) -> visitor.visitTryCatchBlock(a, b, c, name));
        context.setState(CompileState.CODE_BODY);
    }
    
}
