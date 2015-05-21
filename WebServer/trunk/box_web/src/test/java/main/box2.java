package main;

import com.ustc.box.utils.TomcatRunner;


public class box2 {

	public static void main(String[] args) {
		new TomcatRunner("/box2", 8888).run();
	}
}
