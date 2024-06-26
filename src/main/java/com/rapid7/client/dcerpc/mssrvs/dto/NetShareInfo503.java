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

import java.util.Objects;

public class NetShareInfo503 extends NetShareInfo502 {

    private final String serverName;

    public NetShareInfo503(final String netName, final int type, final String remark,
            final int permissions, final int maxUses, final int currentUses, final String path,
            final String passwd, final String serverName, final byte[] securityDescriptor) {
        super(netName, type, remark, permissions, maxUses, currentUses, path, passwd, securityDescriptor);
        this.serverName = serverName;
    }

    public String getServerName() {
        return this.serverName;
    }

    @Override
    public int hashCode() {
        return (31 * super.hashCode()) + Objects.hash(getServerName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof NetShareInfo503)) {
            return false;
        }
        final NetShareInfo503 other = (NetShareInfo503) obj;
        return super.equals(obj)
                && Objects.equals(getServerName(), other.getServerName());
    }

    @Override
    public String toString() {
        return String.format("NetShareInfo503{netName: %s, type: %d, remark: %s, " +
                        "permissions: %d, maxUses: %d, currentUses: %d, path: %s, " +
                        "passwd: %s, serverName: %s, size(securityDescriptor): %s}",
                formatString(getNetName()), getType(), formatString(getRemark()),
                getPermissions(), getMaxUses(), getCurrentUses(), formatString(getPasswd()),
                formatString(getPasswd()), formatString(getServerName()),
                (super.securityDescriptor == null ? "null" : super.securityDescriptor.length));
    }
}
