package com.lnu.soagamble2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ServerMessageGeneratorService")
public interface ServerMessageGeneratorService extends RemoteService
{
    void start();
}

