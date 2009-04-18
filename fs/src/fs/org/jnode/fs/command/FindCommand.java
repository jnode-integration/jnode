/*
 * $Id: CdCommand.java 4975 2009-02-02 08:30:52Z lsantha $
 *
 * Copyright (C) 2003-2009 JNode.org
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
 * along with this library; If not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.jnode.fs.command;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jnode.shell.AbstractCommand;
import org.jnode.shell.syntax.Argument;
import org.jnode.shell.syntax.FileArgument;
import org.jnode.shell.syntax.LongArgument;
import org.jnode.shell.syntax.StringArgument;

/**
 * <code>FindCommand</code> - search for files in a directory hierarchy
 * 
 * @author Alexander Kerner
 * @see AbstractDirectoryWalker
 * 
 */
public class FindCommand extends AbstractCommand {

    private class Walker extends AbstractDirectoryWalker {

        @Override
        protected void handleRestrictedFile(File file) throws IOException {
            err.println("Permission denied for \"" + file + "\"");
        }

        @Override
        public void handleDir(File f) {
            out.println(f);
        }

        @Override
        public void handleFile(File f) {
            out.println(f);
        }
    }

    private final StringArgument nameArg =
            new StringArgument("name", Argument.OPTIONAL,
                    "filter results to show only files that match given pattern");
    private final StringArgument inameArg =
            new StringArgument("iname", Argument.OPTIONAL, "same like 'name', but case insensitive");
    private final LongArgument maxdepthArg =
            new LongArgument("maxdepth", Argument.OPTIONAL,
                    "descent at most to given level of directories");
    private final LongArgument mindepthArg =
            new LongArgument("mindepth", Argument.OPTIONAL,
                    "ignore files and directories at levels less than given level");
    private final StringArgument typeArg =
            new StringArgument(
                "type",
                Argument.OPTIONAL,
                "filter results to show only files of given type. valid types are 'd' for directory and 'f' for file");
    private final FileArgument dirArg =
            new FileArgument("directory", Argument.OPTIONAL | Argument.MULTIPLE,
                    "directory to start searching from");
    private PrintWriter out = null;
    private PrintWriter err = null;

    public FindCommand() {
        super("Find files and directories");
        registerArguments(dirArg, mindepthArg, maxdepthArg, inameArg, nameArg, typeArg);
    }

    public static void main(String[] args) throws IOException {
        new FindCommand().execute();
    }

    public void execute() throws IOException {
        out = getOutput().getPrintWriter();
        err = getError().getPrintWriter();
        final Walker walker = new Walker();

        if (maxdepthArg.isSet()) {
            walker.setMaxDepth(maxdepthArg.getValue());
        }

        if (mindepthArg.isSet()) {
            walker.setMinDepth(mindepthArg.getValue());
        }

        if (nameArg.isSet()) {
            final String value = nameArg.getValue();
            walker.addFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    Pattern p = Pattern.compile(value);
                    Matcher m = p.matcher(file.getName());
                    return m.matches();
                }
            });
        }

        if (inameArg.isSet()) {
            final String value = inameArg.getValue();
            walker.addFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    Pattern p = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(file.getName());
                    return m.matches();
                }
            });
        }

        if (typeArg.isSet()) {
            final Character value = typeArg.getValue().charAt(0);
            if (value.equals(Character.valueOf('f'))) {
                walker.addFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isFile();
                    }
                });
            } else if (value.equals(Character.valueOf('d'))) {
                walker.addFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory();
                    }
                });
            }
        }
        if (dirArg.isSet()) {
            walker.walk(dirArg.getValues());
        } else {
            walker.walk(new File(System.getProperty("user.dir")));
        }
    }
}