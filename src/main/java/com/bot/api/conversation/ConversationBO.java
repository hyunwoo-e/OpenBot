/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.conversation;

import com.bot.api.config.DialogMapper;
import com.bot.api.config.ServerConfig;
import com.bot.api.config.UserMapper;
import com.bot.api.model.conversation.ConversationRequest;
import com.bot.api.model.conversation.ConversationResponse;
import com.bot.api.model.nlu.NLUEntity;
import com.bot.api.model.nlu.NLURequest;
import com.bot.api.model.nlu.NLUResponse;
import com.bot.api.model.nlu.NLUValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ConversationBO {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DialogMapper dialogMapper;

    public ConversationResponse response(ConversationRequest conversationRequest) throws Exception {
        if (!userMapper.containsKey(conversationRequest.getUserKey())) {
            userMapper.initUser(conversationRequest.getUserKey());
        }

        NLUResponse nluResponse = NLU(conversationRequest.getText());
        setEntities(conversationRequest.getUserKey(), nluResponse);

        if(!userMapper.get(conversationRequest.getUserKey()).getDialog().equals("#None")) {
            ConversationResponse conversationResponse = dialogMapper.getDialog(userMapper.get(conversationRequest.getUserKey()).getDialog()).response(conversationRequest.getUserKey());
            if(userMapper.get(conversationRequest.getUserKey()).getTryCount() > 1) {
                conversationResponse = dialogMapper.getDialog("#None").response(conversationRequest.getUserKey());
            }
            return conversationResponse;
        } else {
            userMapper.get(conversationRequest.getUserKey()).setDialog(nluResponse.getIntents().get(0).getIntent());
            return dialogMapper.getDialog(nluResponse.getIntents().get(0).getIntent()).response(conversationRequest.getUserKey());
        }
    }

    public NLUResponse NLU(String text) {
        String url = "http://"+ ServerConfig.nlu_server_ip+":"+ ServerConfig.nlu_server_port;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        HttpEntity<NLURequest> httpEntity = new HttpEntity<NLURequest>(NLURequest.valueOf(text), headers);
        return new RestTemplate().postForObject(url + "/api/message", httpEntity, NLUResponse.class);
    }

    private void setEntities(String userKey, NLUResponse nluResponse) {
        if(userMapper.get(userKey).getEntityMap() == null) {
            userMapper.get(userKey).setEntityMap(new HashMap<String, ArrayList<NLUValue>>());
        }

        for(NLUEntity nluEntity : nluResponse.getEntities()) {
            if(userMapper.get(userKey).getEntityMap().get(nluEntity.getEntity()) == null) {
                userMapper.get(userKey).getEntityMap().put(nluEntity.getEntity(), new ArrayList<NLUValue>());
            }
            for(NLUValue nluValue : nluEntity.getValues()) {
                userMapper.get(userKey).getEntityMap().get(nluEntity.getEntity()).add(nluValue);
            }
        }
    }
}