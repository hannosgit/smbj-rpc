/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mssrvs.dto;

import com.hierynomus.msdtyp.SecurityDescriptor;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.hierynomus.smbj.common.SMBRuntimeException;

import java.util.Arrays;

public class NetShareInfo502 extends NetShareInfo2 {

    protected final byte[] securityDescriptor;

    public NetShareInfo502(final String netName, final int type, final String remark,
            final int permissions, final int maxUses, final int currentUses, final String path,
            final String passwd, final byte[] securityDescriptor) {
        super(netName, type, remark, permissions, maxUses, currentUses, path, passwd);
        this.securityDescriptor = securityDescriptor;
    }

    public SecurityDescriptor getSecurityDescriptor() {
        if(securityDescriptor == null){
            return null;
        }
        try {
            return SecurityDescriptor.read(new SMBBuffer(securityDescriptor));
        } catch (Buffer.BufferException e) {
            throw new SMBRuntimeException(e);
        }
    }

    @Override
    public int hashCode() {
        return (31 * super.hashCode()) + Arrays.hashCode(this.securityDescriptor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof NetShareInfo502)) {
            return false;
        }
        final NetShareInfo502 other = (NetShareInfo502) obj;
        return super.equals(obj)
                && Arrays.equals(this.securityDescriptor, other.securityDescriptor);
    }

    @Override
    public String toString() {
        return String.format("NetShareInfo502{netName: %s, type: %d, remark: %s, " +
                        "permissions: %d, maxUses: %d, currentUses: %d, path: %s, " +
                        "passwd: %s, size(securityDescriptor): %s}",
                formatString(getNetName()), getType(), formatString(getRemark()),
                getPermissions(), getMaxUses(), getCurrentUses(), formatString(getPasswd()),
                formatString(getPasswd()), (this.securityDescriptor == null ? "null" : this.securityDescriptor.length));
    }
}
