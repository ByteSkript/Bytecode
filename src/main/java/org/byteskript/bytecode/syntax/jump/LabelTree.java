package org.byteskript.bytecode.syntax.jump;

import org.byteskript.skript.api.SyntaxElement;
import org.byteskript.skript.compiler.Context;
import org.byteskript.skript.compiler.structure.MultiLabel;
import org.byteskript.skript.compiler.structure.ProgrammaticSplitTree;
import org.byteskript.skript.compiler.structure.SectionMeta;
import org.objectweb.asm.Label;

import java.util.HashMap;
import java.util.Map;

public class LabelTree extends ProgrammaticSplitTree {
    
    final Map<Integer, Label> labels = new HashMap<>();
    final SectionMeta owner;
    
    public LabelTree(SectionMeta owner) {
        this.owner = owner;
    }
    
    public Label getLabel(int index) {
        this.labels.putIfAbsent(index, new Label());
        return labels.get(index);
    }
    
    @Override
    public SectionMeta owner() {
        return owner;
    }
    
    @Override
    public MultiLabel getEnd() {
        return null;
    }
    
    @Override
    public void start(Context context) {
    
    }
    
    @Override
    public void branch(Context context) {
    
    }
    
    @Override
    public void close(Context context) {
    
    }
    
    @Override
    public boolean permit(SyntaxElement syntaxElement) {
        return false;
    }
    
    @Override
    public boolean isOpen() {
        return true;
    }
}
