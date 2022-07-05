package com.ctc.exception;

/**
 * @author ankit pathak
 *
 */
public class MemberNotFoundException extends Exception{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException(String str) {
		super(str);
	}
}
