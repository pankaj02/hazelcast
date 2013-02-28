/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.map;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spi.AbstractOperation;

import java.io.IOException;

public class MapTxnBackupRollbackOperation extends AbstractOperation {
    String txnId;

    public MapTxnBackupRollbackOperation(String txnId) {
        this.txnId = txnId;
    }

    public MapTxnBackupRollbackOperation() {
    }

    public void run() {
        int partitionId = getPartitionId();
        MapService mapService = (MapService) getService();
        System.out.println(getNodeEngine().getThisAddress() + " backupRollback " + txnId);
        PartitionContainer partitionContainer = mapService.getPartitionContainer(partitionId);
        partitionContainer.rollback(txnId);
    }

    @Override
    protected void writeInternal(ObjectDataOutput out) throws IOException {
        super.writeInternal(out);
        out.writeUTF(txnId);
    }

    @Override
    protected void readInternal(ObjectDataInput in) throws IOException {
        super.readInternal(in);
        txnId = in.readUTF();
    }
}