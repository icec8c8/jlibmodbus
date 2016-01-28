package com.sbpinvertor.modbus.net.stream;

import com.sbpinvertor.modbus.Modbus;
import com.sbpinvertor.modbus.net.stream.base.ModbusOutputStream;
import com.sbpinvertor.modbus.serial.SerialPort;
import com.sbpinvertor.modbus.utils.ByteFifo;
import com.sbpinvertor.modbus.utils.DataUtils;

import java.io.IOException;

/**
 * Copyright (c) 2015-2016 JSC "Zavod "Invertor"
 * [http://www.sbp-invertor.ru]
 * <p/>
 * This file is part of JLibModbus.
 * <p/>
 * JLibModbus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * Authors: Vladislav Y. Kochedykov, software engineer.
 * email: vladislav.kochedykov@gmail.com
 */
public class OutputStreamASCII extends ModbusOutputStream {
    final private SerialPort serial;
    final private ByteFifo fifo = new ByteFifo(Modbus.MAX_RTU_ADU_LENGTH);
    private int lrc = 0;

    public OutputStreamASCII(SerialPort serial) {
        this.serial = serial;
        reset();
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        for (byte b : bytes) {
            lrc += b;
        }
        byte[] ascii = DataUtils.toAscii(bytes);
        fifo.write(ascii);
    }

    @Override
    public void write(int b) throws IOException {
        lrc += (byte) b;
        byte[] bytes = DataUtils.toAscii((byte) b);
        fifo.write(bytes);
    }

    @Override
    public void flush() throws IOException {
        fifo.write(DataUtils.toAscii((byte) (lrc = -lrc)));
        fifo.write(Modbus.ASCII_CODE_CR);
        fifo.write(Modbus.ASCII_CODE_LF);
        serial.write(fifo.toByteArray());
        reset();
    }

    @Override
    public void reset() {
        fifo.clear();
        fifo.write(Modbus.ASCII_CODE_COLON);
        lrc = 0;
    }
}