package minitwitter;

import java.awt.EventQueue;

import minitwitter.ui.AdminControlPanelUI;

public class Driver {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AdminControlPanelUI();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
