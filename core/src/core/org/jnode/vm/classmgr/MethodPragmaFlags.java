/*
 * $Id$
 *
 * JNode.org
 * Copyright (C) 2006 JNode.org
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, 
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 */
 
package org.jnode.vm.classmgr;

/**
 * Variable pragma flags for methods.
 * 
 * @see org.jnode.vm.classmgr.VmMethod#pragmaFlags
 * @author Ewout Prangsma (epr@users.sourceforge.net)
 */
public interface MethodPragmaFlags {

    /** Method will not get any yieldpoints */
    public static final char UNINTERRUPTIBLE = 0x0001;
    
    /** Method will be inlined (if possible) */
    public static final char INLINE = 0x0002;
    
    /** Method will not be inlined */
    public static final char NOINLINE = 0x0004;
    
    /** Method header will reload the statics register */
    public static final char LOADSTATICS = 0x0008;
    
    /** Method used to implemented Privileged action */
    public static final char DOPRIVILEGED = 0x0010;
    
    /** Method used to implemented Privileged action */
    public static final char CHECKPERMISSION = 0x0020;
    
    /** Method will behave like a Privileged action */
    public static final char PRIVILEGEDACTION = 0x0040;
    
    /** No read barriers will be emitted */
    public static final char NOREADBARRIER = 0x0080;
    
    /** No write barriers will be emitted */
    public static final char NOWRITEBARRIER = 0x0100;
    
}
