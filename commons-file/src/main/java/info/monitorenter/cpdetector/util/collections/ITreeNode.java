

package info.monitorenter.cpdetector.util.collections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public interface ITreeNode {
  public static ITreeNode ROOT = new DefaultTreeNode();

  public Object getUserObject();

  
  public Object setUserObject(Object store);

  
  public void mark();

  
  public void unmark();

  
  public boolean isMarked();

  
  public int getChildCount();

  
  public int getSubtreeCount();

  
  public Iterator getChilds();

  
  public ITreeNode getParent();

  
  public void setParent(ITreeNode parent);

  
  public boolean addChildNode(ITreeNode node);

  
  public boolean addChildNodes(ITreeNode[] nodes);

  public ITreeNode addChild(Object userObject);

  

  public ITreeNode[] addChildren(Object[] userObjects);

  
  public boolean removeChild(ITreeNode node);

  
  public ITreeNode remove(Object userObject);

  
  public List removeAllChildren();

  

  public List getAllChildren();

  
  public boolean contains(Object userObject);

  
  public boolean containsNode(ITreeNode node);

  
  public boolean isLeaf();

  
  public boolean isRoot();

  
  public void getPathFromRoot(List l);

  
  public void getUserObjectPathFromRoot(List l);

  
  public boolean equals(Object o);

  
  public ITreeNode newInstance();

  
  public static class DefaultTreeNode implements ITreeNode, Comparable<ITreeNode> {
    
    protected boolean marked = false;

    
    public boolean equals(Object obj) {
      boolean ret = false;
      if (obj instanceof DefaultTreeNode) {
        DefaultTreeNode other = (DefaultTreeNode) obj;
        Object myUser = this.getUserObject();
        Object himUser = other.getUserObject();
        ret = (myUser == null) ? ((himUser == null) ? true : false) : (myUser.equals(himUser));
      }
      return ret;
    }

    
    protected Object m_userObject = null;

    
    ITreeNode m_parent = null;

    
    protected SortedSet m_children;

    
    public DefaultTreeNode() {
      this.m_children = new TreeSet();
      this.m_userObject = "root";
    }

    
    public DefaultTreeNode(final Object userObject) {
      this();
      this.m_userObject = userObject;
    }

    
    public DefaultTreeNode(final Object userObject, final ITreeNode child) {
      this(userObject);
      this.addChildNode(child);
    }

    
    public DefaultTreeNode(final Object userObject, final ITreeNode[] children) {
      this(userObject);
      for (int i = 0; i < children.length; i++) {
        this.addChildNode(children[i]);
      }
    }

    
    public final ITreeNode addChild(final Object userObject) {
      ITreeNode ret = this.newInstance();
      ret.setUserObject(userObject);
      if (this.addChildNode(ret)) {
        return ret;
      } else {
        return ret.getParent();
      }
    }

    
    public boolean addChildNode(final ITreeNode node) {
      if (node == null) {
        return false;
      }
      node.setParent(this);
      this.m_children.add(node);
      return true;
    }

    
    public final boolean contains(Object userObject) {
      if ((this.m_userObject != null) && (this.m_userObject.equals(userObject))) {
        return true;
      } else {
        if (!this.isLeaf()) {
          Iterator it = this.m_children.iterator();
          while (it.hasNext()) {
            if (((ITreeNode) it.next()).contains(userObject))
              return true;
          }
          return false;
        }
        return false;
      }
    }

    
    public final boolean containsNode(ITreeNode node) {
      if (this.equals(node)) {
        return true;
      } else {
        if (!this.isLeaf()) {
          Iterator it = this.m_children.iterator();
          while (it.hasNext()) {
            if (((ITreeNode) it.next()).contains(node)) {
              return true;
            }
          }
          return false;
        }
        return false;
      }
    }

    
    public final int getChildCount() {
      return this.m_children.size();
    }

    
    public final Iterator getChilds() {
      return this.m_children.iterator();
    }

    
    public final ITreeNode getParent() {
      return (this.m_parent == null) ? ROOT : this.m_parent;
    }

    
    public final int getSubtreeCount() {
      // hehehe: clever double-use of child detection and partial result...
      int ret = this.m_children.size();
      if (ret > 0) {
        Iterator it = this.m_children.iterator();
        while (it.hasNext()) {
          ret += ((ITreeNode) it.next()).getSubtreeCount();
        }

      }
      if (this.m_parent == ROOT) {
        ret++; // root has to count itself...
      }
      return ret;
    }

    
    public final Object getUserObject() {
      return this.m_userObject;
    }

    
    public final void mark() {
      this.marked = true;
    }

    public final boolean isMarked() {
      return this.marked;
    }

    
    public final ITreeNode remove(final Object userObject) {
      ITreeNode ret = null;
      if ((this.m_userObject != null) && (this.m_userObject.equals(userObject))) {
        this.m_parent.removeChild(this);
        this.m_parent = null;
        ret = this;
      } else {
        if (!this.isLeaf()) {
          Iterator it = this.m_children.iterator();
          while (it.hasNext()) {
            ret = ((ITreeNode) it.next());
            if (ret != null) {
              break;
            }
          }
        } else
          return null;
      }
      return ret;
    }

    
    public final List removeAllChildren() {
      SortedSet ret = this.m_children;
      Iterator it = ret.iterator();
      while (it.hasNext()) {
        ((ITreeNode) it.next()).setParent(null);
      }
      this.m_children = new TreeSet();
      return new LinkedList(ret);
    }

    
    public boolean removeChild(ITreeNode node) {
      return this.m_children.remove(node);
    }

    
    public final Object setUserObject(Object store) {
      Object ret = this.m_userObject;
      this.m_userObject = store;
      return ret;
    }

    
    public final void unmark() {
      this.marked = false;
    }

    
    public final void setParent(final ITreeNode parent) {
      if (this.m_parent != null) {
        // will call: node.setParent(null);
        this.m_parent.removeChild(this);
      }
      this.m_parent = parent;

    }

    
    public final boolean isLeaf() {
      return this.m_children.size() == 0;
    }

    
    public final boolean isRoot() {
      return this.m_parent == null;
    }

    public String toString() {

      StringBuffer ret = new StringBuffer();
      this.toStringInternal(ret, 1);
      return ret.toString();
    }

    protected void toStringInternal(StringBuffer buf, int depth) {
      if (this.isLeaf()) {
        buf.append("-> ");
      }
      buf.append('(').append(String.valueOf(this.m_userObject)).append(')');
      StringBuffer spaceCollect = new StringBuffer();
      for (int i = depth; i > 0; i--) {
        spaceCollect.append("  ");
      }
      String indent = spaceCollect.toString();
      Iterator it = this.getChilds();
      while (it.hasNext()) {
        buf.append("\n").append(indent);
        ((DefaultTreeNode) it.next()).toStringInternal(buf, depth + 1);
      }
    }

    
    public final boolean addChildNodes(ITreeNode[] nodes) {
      boolean ret = true;
      for (int i = 0; i < nodes.length; i++) {
        ret &= this.addChildNode(nodes[i]);
      }
      return ret;
    }

    
    public final ITreeNode[] addChildren(Object[] userObjects) {
      List treeNodes = new LinkedList(); // can't know the size, as they might
      // contain null.
      ITreeNode newNode = null;
      for (int i = 0; i < userObjects.length; i++) {
        newNode = this.addChild(userObjects[i]);
        if (newNode != null) {
          treeNodes.add(newNode);
        }
      }

      return (ITreeNode[]) treeNodes.toArray(new ITreeNode[treeNodes.size()]);
    }

    
    public final List getAllChildren() {
      return new LinkedList(this.m_children);
    }

    
    public ITreeNode newInstance() {
      return new DefaultTreeNode();
    }

    public void getPathFromRoot(List l) {
      if (this.isRoot()) {
        l.add(this);
      } else {
        this.getParent().getPathFromRoot(l);
        l.add(this);
      }
    }

    
    public void getUserObjectPathFromRoot(List l) {
      List collect = new LinkedList();
      this.getPathFromRoot(collect);
      Iterator it = collect.iterator();
      while (it.hasNext()) {
        l.add(((ITreeNode) it.next()).getUserObject());
      }
    }

    public int compareTo(final ITreeNode o) throws ClassCastException {
      ITreeNode other = (ITreeNode) o;
      return ((Comparable) this.m_userObject).compareTo(other.getUserObject());
    }

  }
}
