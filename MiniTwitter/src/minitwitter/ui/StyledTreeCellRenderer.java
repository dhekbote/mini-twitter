package minitwitter.ui;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import minitwitter.model.User;
import minitwitter.model.UserGroup;

public class StyledTreeCellRenderer extends DefaultTreeCellRenderer {
    private ImageIcon groupIcon;
    private ImageIcon userIcon;

    public StyledTreeCellRenderer() {
        // Load images
    	 groupIcon = new ImageIcon(new ImageIcon("folder_icon.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
         userIcon = new ImageIcon(new ImageIcon("user_icon.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));  
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        // Set custom icons
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.getUserObject() instanceof UserGroup) {
                setIcon(groupIcon);
                setText(((UserGroup) node.getUserObject()).getId());
            } else if (node.getUserObject() instanceof User) {
                setIcon(userIcon);
                setText(((User) node.getUserObject()).getId());
            }
        }
        return this;
    }
}