package com.invertor.modbus.master;

import com.invertor.modbus.ModbusMaster;
import com.invertor.modbus.exception.ModbusIOException;
import com.invertor.modbus.exception.ModbusMasterException;
import com.invertor.modbus.exception.ModbusNumberException;
import com.invertor.modbus.exception.ModbusProtocolException;
import com.invertor.modbus.msg.base.ModbusMessage;
import com.invertor.modbus.msg.response.ReadExceptionStatusResponse;
import com.invertor.modbus.msg.response.ReportSlaveIdResponse;

/**
 * Copyright (c) 2015-2016 JSC Invertor
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
abstract public class ModbusMasterSerial extends ModbusMaster {
    @Override
    public int readExceptionStatus(int serverAddress) throws
            ModbusProtocolException, ModbusNumberException, ModbusIOException, ModbusMasterException {
        ModbusMessage request = requestFactory.createReadExceptionStatus(serverAddress);
        ReadExceptionStatusResponse response = (ReadExceptionStatusResponse) processRequest(request);
        return response.getExceptionStatus();
    }

    @Override
    public byte[] reportSlaveId(int serverAddress) throws ModbusProtocolException, ModbusNumberException, ModbusIOException, ModbusMasterException {
        ModbusMessage request = requestFactory.createReportSlaveId(serverAddress);
        ReportSlaveIdResponse response = (ReportSlaveIdResponse) processRequest(request);
        return response.getSlaveId();
    }
}