package com;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: oleksiizozulenko
 * Date: 7/7/13
 * Time: 11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestProToString {

	public static void main(String [] args) {
		String propString = "INTERVAL=1\nFREQ=DAILY";
		Properties p = new Properties();
		try {
			p.load(new StringReader(propString));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
