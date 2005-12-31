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
 
package org.jnode.test.fs.filesystem.config;

import org.jnode.fs.FileSystem;
import org.jnode.test.fs.filesystem.FSContext;
import org.jnode.test.support.TestConfig;

/**
 * @author Fabien DUMINY
 */
public class FSTestConfig implements TestConfig {

    final private DeviceParam deviceParam;

    final private OsType os;

    final private FS fs;

    /**
     * 
     *
     */
    public FSTestConfig(OsType osType, FS fs, DeviceParam deviceParam) {
        deviceParam.setInput(false);
        this.deviceParam = deviceParam;
        this.os = osType;
        this.fs = fs;
    }

    final public Class getContextClass() {
        return FSContext.class;
    }

    /**
     * @return Returns the fs.
     */
    public FS getFileSystem() {
        return fs;
    }

    /**
     * @return
     */
    public Class<? extends FileSystem> getFsClass() {
        return fs.getType().getFsClass();
    }

    /**
     * @return
     */
    public boolean isReadOnly() {
        return fs.isReadOnly();
    }

    public DeviceParam getDeviceParam() {
        return deviceParam;
    }

    /**
     * 
     */
    public String toString() {
        return os.toString() + "," + fs + "\n" + deviceParam;
    }

    public String getName() {
        return toString();
    }
}
