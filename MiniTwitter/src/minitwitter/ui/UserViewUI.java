package minitwitter.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import minitwitter.admin.AdminControlPanel;
import minitwitter.model.User;
import minitwitter.observer.Observer;

public class UserViewUI implements Observer {

	private AdminControlPanel admin;
	private JFrame frmUserView;
	private User user;

	private JTextArea txtFollowUserId;
	private JTextArea txtTweetMessage;
	private JList<String> listCurrentFollowingUsers;
	private JList<String> listNewsfeed;

	public UserViewUI(User user, AdminControlPanel admin) {
		this.user = user;
		this.admin = admin;
		initialize();
		frmUserView.setVisible(true);

	}

	private void initialize() {
		frmUserView = new JFrame();
		frmUserView.setTitle(user.getId());
		frmUserView.setBounds(100, 100, 450, 387);
		frmUserView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmUserView.getContentPane().setLayout(null);

		txtFollowUserId = new JTextArea();
		txtFollowUserId.setBounds(10, 11, 199, 22);
		frmUserView.getContentPane().add(txtFollowUserId);

		JButton btnFollowUser = new JButton("Follow User");
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				followUser();

			}
		});
		btnFollowUser.setBounds(219, 12, 205, 23);
		frmUserView.getContentPane().add(btnFollowUser);

		txtTweetMessage = new JTextArea();
		txtTweetMessage.setBounds(10, 170, 199, 22);
		frmUserView.getContentPane().add(txtTweetMessage);

		JButton btnPostTweet = new JButton("Post Tweet");
		btnPostTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postTweet();
			}
		});
		btnPostTweet.setBounds(219, 171, 205, 23);
		frmUserView.getContentPane().add(btnPostTweet);

		JScrollPane scrollPaneFollowing = new JScrollPane();
		scrollPaneFollowing.setBounds(10, 44, 414, 100);
		frmUserView.getContentPane().add(scrollPaneFollowing);

		listCurrentFollowingUsers = new JList<String>();
		scrollPaneFollowing.setViewportView(listCurrentFollowingUsers);

		listNewsfeed = new JList<String>();

		JScrollPane scrollPaneNewsFeed = new JScrollPane();
		scrollPaneNewsFeed.setBounds(10, 218, 414, 100);
		scrollPaneNewsFeed.setViewportView(listNewsfeed);

		frmUserView.getContentPane().add(scrollPaneNewsFeed);

		populateCurrentFollowingUsersList();
		populateNewsfeedList();
	}

	protected void postTweet() {
		String tweetMessage = txtTweetMessage.getText();
		if (tweetMessage.length() > 0) {
			user.postTweet(tweetMessage);
			populateNewsfeedList();
		}

	}

	private void populateNewsfeedList() {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		listModel.addElement("News Feed");
		for (String news : user.getNewsFeed()) {
			listModel.addElement("- " + news);
		}
		listNewsfeed.setModel(listModel);

	}

	private void populateCurrentFollowingUsersList() {

		DefaultListModel<String> listModel = new DefaultListModel<>();
		listModel.addElement("Current Following");
		for (String following : user.getFollowings()) {
			listModel.addElement("- " + following);
		
		}
		listCurrentFollowingUsers.setModel(listModel);
	}

	public void followUser() {
		String followUserID = txtFollowUserId.getText();
		User followUser = admin.getUserById(followUserID);
		if (followUser != null && followUser != user) {
			user.follow(followUser);
			followUser.registerObserver(this);
			populateCurrentFollowingUsersList();

		}
	}

	@Override
	public void update(String tweet) {
		
		populateNewsfeedList();

	}
}
