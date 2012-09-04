package com.sonyericsson.chkbugreport.doc;

import java.io.IOException;
import java.util.Vector;

/**
 * The DocNode class represents a piece of text or data from the generated report.
 * It can contain child nodes (for example a list can contain the list items as child nodes).
 * Currently it needs to support html rendering, but in the future it could support other formats
 * as well.
 */
public class DocNode {

    private Vector<DocNode> mChildren = new Vector<DocNode>();
    private DocNode mParent = null;

    public DocNode() {
    }

    public DocNode(DocNode parent) {
        if (parent != null) {
            parent.add(this);
        }
    }

    public int getChildCount() {
        return mChildren.size();
    }

    public DocNode getChild(int idx) {
        return mChildren.get(idx);
    }

    public DocNode getParent() {
        return mParent;
    }

    public DocNode add(DocNode child) {
        mChildren.add(child);
        child.mParent = this;
        return this;
    }

    public DocNode add(String text) {
        return add(new SimpleText(text));
    }

    public DocNode addln(String text) {
        return add(new SimpleText(text + '\n'));
    }

    /**
     * This method is called when all the data is collected/generated and
     * it's time to render the content. This method is used to calculate the
     * generate file names and links.
     * @param r The Renderer
     */
    public void prepare(Renderer r) {
        for (DocNode child : mChildren) {
            child.prepare(r);
        }
    }

    /**
     * Renders this node and all the child nodes
     * @param r The Renderer
     */
    public void render(Renderer r) throws IOException {
        renderChildren(r);
    }

    /**
     * Render all the child nodes
     * @param r The Renderer
     */
    protected void renderChildren(Renderer r) throws IOException {
        for (DocNode child : mChildren) {
            child.render(r);
        }
    }

    public String getText() {
        StringBuffer sb = new StringBuffer();
        for (DocNode child : mChildren) {
            sb.append(child.getText());
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return mChildren.isEmpty();
    }

}
