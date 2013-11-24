package com.moomoohk.Grame.Interfaces;


/**
 * This interface is used to create generators which generate names and types
 * for Entities.
 * 
 * @see OldEntity
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public interface EntityGenerator
{
	/**
	 * Generates a name.
	 * 
	 * @return A name.
	 */
	public abstract String nameGen();

	/**
	 * Generates a type.
	 * 
	 * @return A type.
	 */
	public abstract String typeGen();
}
