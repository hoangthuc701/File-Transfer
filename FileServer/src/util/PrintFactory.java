package util;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class PrintFactory {
	public PrintFactory() {
	}

	public static void printCurrentStatistics(int totalTransferred, int previousSize, StartTime timer,
			double previousTimeElapsed) {
		printSpace();
		printSpace();
		printSeperator();
		System.out.println("Milestone Statistics");

		int sizeDifference = totalTransferred / 1000 - previousSize;
		double difference = timer.getTimeElapsed() - previousTimeElapsed;
		double throughput = totalTransferred / 1000 / timer.getTimeElapsed();

		System.out.println("We just receieved another: " + sizeDifference + "Kb");
		System.out.println("You have now receieved " + totalTransferred / 1000 + "Kb");
		System.out.println("Time taken so far: " + timer.getTimeElapsed() / 1000 + " Seconds");
		System.out.println("Throughput average so far :" + throughput + "Mbps");
		System.out.println("Throughput for last 50: " + sizeDifference / difference + "Mbps");

		printSeperator();
		printSpace();
		printSpace();

	}

	public static void printSpace() {
		System.out.println();
	}

	public static void printSeperator() {
		System.out.println("------------------------------");
	}

	public static void writeLogs(JTextPane tp, String msg) {
		appendToPane(tp, msg, Color.BLACK);
		appendToPane(tp, "\n", Color.BLACK);
	}

	public static void writeError(JTextPane tp, String msg) {
		appendToPane(tp, msg, Color.RED);
		appendToPane(tp, "\n", Color.RED);
	}

	public static void writeSuccess(JTextPane tp, String msg) {
		appendToPane(tp, msg, Color.GREEN);
		appendToPane(tp, "\n", Color.GREEN);
	}

	private static void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

}
