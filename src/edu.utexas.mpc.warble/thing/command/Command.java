/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.utexas.mpc.warble.thing.command;

public abstract class Command {
    public enum COMMAND_NAME {
        AUTHENTICATE,
        SET_THING_STATE
    }

    protected COMMAND_NAME name;
    protected Object header;
    protected Object flag1;
    protected Object flag2;
    protected Object flag3;
    protected Object flag4;
    protected Object flag5;
    protected Object flag6;
    protected Object register1;
    protected Object register2;
    protected Object register3;
    protected Object register4;
    protected Object register5;
    protected Object register6;

    public COMMAND_NAME getName() {
        return name;
    }

    public Object getHeader() {
        return header;
    }

    public Object getRegister1() {
        return register1;
    }

    public Object getRegister2() {
        return register2;
    }

    public Object getRegister3() {
        return register3;
    }

    public Object getRegister4() {
        return register4;
    }

    public Object getRegister5() {
        return register5;
    }

    public Object getRegister6() {
        return register6;
    }
}
