package minitwitter.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import minitwitter.admin.AdminControlPanel;
import minitwitter.model.User;
import minitwitter.model.UserGroup;

public class AdminControlPanelUI {

	private JFrame frmAdminControlPanel;
	private AdminControlPanel admin;

	private JTree treeView;
	private DefaultTreeModel treeModel;

	private JTextArea txtGroupId;
	private JTextArea txtUserId;

	private boolean onlyRootGroup;
	private UserGroup rootGroup;
	private DefaultMutableTreeNode rootNode;

	public AdminControlPanelUI() {
		admin = AdminControlPanel.getInstance();
		initialize();
		frmAdminControlPanel.setVisible(true);
	}

	
	private void initialize() {
		frmAdminControlPanel = new JFrame();
		frmAdminControlPanel.setTitle("Admin Control Panel");
		frmAdminControlPanel.setBounds(100, 100, 611, 300);
		frmAdminControlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdminControlPanel.getContentPane().setLayout(null);

		rootGroup = new UserGroup("Root");
		User user1 = new User("John");
		rootGroup.add(user1);

		rootNode = new DefaultMutableTreeNode(rootGroup);
		DefaultMutableTreeNode userNode1 = new DefaultMutableTreeNode(user1);
		rootNode.add(userNode1);
		onlyRootGroup = true;
		admin.addUserGroup(rootGroup);
		admin.addUser(user1);
		

		treeModel = new DefaultTreeModel(rootNode);
		treeView = new JTree(treeModel);
		treeView.setCellRenderer(new StyledTreeCellRenderer());

		treeView.setBounds(0, 0, 174, 260);
		frmAdminControlPanel.getContentPane().add(treeView);

		txtUserId = new JTextArea();
		txtUserId.setBounds(184, 11, 211, 22);
		frmAdminControlPanel.getContentPane().add(txtUserId);

		txtGroupId = new JTextArea();
		txtGroupId.setBounds(184, 44, 211, 22);
		frmAdminControlPanel.getContentPane().add(txtGroupId);

		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				addUser();
			}
		});
		btnAddUser.setBounds(421, 10, 166, 23);
		frmAdminControlPanel.getContentPane().add(btnAddUser);

		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addUserGroup();

			}
		});
		btnAddGroup.setBounds(421, 43, 166, 23);
		frmAdminControlPanel.getContentPane().add(btnAddGroup);

		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openUserView();

			}
		});
		btnOpenUserView.setBounds(184, 77, 403, 23);
		frmAdminControlPanel.getContentPane().add(btnOpenUserView);

		JButton btnShowUserTotal = new JButton("Show User Total");
		btnShowUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Total Users: " + admin.getTotalUsers());

			}
		});
		btnShowUserTotal.setBounds(184, 198, 177, 23);
		frmAdminControlPanel.getContentPane().add(btnShowUserTotal);

		JButton btnShowMessageTotal = new JButton("Show Messages Total");
		btnShowMessageTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Total Messages: " + admin.getTotalTweets());
			}
		});
		btnShowMessageTotal.setBounds(184, 232, 177, 23);
		frmAdminControlPanel.getContentPane().add(btnShowMessageTotal);

		JButton btnShowGroupTotal = new JButton("Show Group Total");
		btnShowGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Total Groups: " + admin.getTotalGroups());
			}
		});
		btnShowGroupTotal.setBounds(381, 198, 206, 23);
		frmAdminControlPanel.getContentPane().add(btnShowGroupTotal);

		JButton btnShowPositiviePercentage = new JButton("Show Positive Percentage");
		btnShowPositiviePercentage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Positive Tweet Percentage: "
						+ String.format("%.2f", admin.getPositiveTweetPercentage()) + "%");
			}
		});
		btnShowPositiviePercentage.setBounds(381, 232, 206, 23);
		frmAdminControlPanel.getContentPane().add(btnShowPositiviePercentage);

	}

	protected void openUserView() {

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeView.getLastSelectedPathComponent();

		if (selectedNode == null) {
			JOptionPane.showMessageDialog(null, "Please select a user.");
			return;
		}

		Object selectedObject = selectedNode.getUserObject();
		if (selectedObject instanceof User) {
			User selectedUser = (User) selectedObject;
			new UserViewUI(selectedUser, admin);

		} else {
			JOptionPane.showMessageDialog(null, "Please select a user.");
		}

	}

	private void addUserGroup() {
		String groupId = txtGroupId.getText();
		if (groupId.length() > 0) {
			UserGroup newGroup = new UserGroup(groupId);
			admin.addUserGroup(newGroup);

			if (onlyRootGroup) {
				// Add to the root group
				rootGroup.add(newGroup);

				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newGroup);
				rootNode.add(newNode);
				treeModel.nodeStructureChanged(rootNode);
				onlyRootGroup = false;
			} else {

				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeView.getLastSelectedPathComponent();

				if (selectedNode == null) {
					JOptionPane.showMessageDialog(null, "Please select a parent UserGroup.");
					return;
				}

				Object selectedObject = selectedNode.getUserObject();
				if (selectedObject instanceof UserGroup) {
					UserGroup selectedGroup = (UserGroup) selectedObject;
					selectedGroup.add(newGroup);

					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newGroup);
					selectedNode.add(newNode);
					treeModel.nodeStructureChanged(selectedNode);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a parent UserGroup.");
				}
			}
		}
	}

	private void addUser() {
		String userId = txtUserId.getText();

		if (userId.length() > 0) {

			User newUser = new User(userId);
			admin.addUser(newUser);

			if (onlyRootGroup) {

				rootGroup.add(newUser);

				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newUser);
				rootNode.add(newNode);
				treeModel.nodeStructureChanged(rootNode);

			} else {

				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeView.getLastSelectedPathComponent();

				if (selectedNode == null) {
					JOptionPane.showMessageDialog(null, "Please select a parent UserGroup.");
					return;
				}

				Object selectedObject = selectedNode.getUserObject();
				if (selectedObject instanceof UserGroup) {
					UserGroup selectedGroup = (UserGroup) selectedObject;

					selectedGroup.add(newUser);

					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newUser);
					selectedNode.add(newNode);
					treeModel.nodeStructureChanged(selectedNode);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a parent UserGroup.");
				}
			}
		}
	}
}
