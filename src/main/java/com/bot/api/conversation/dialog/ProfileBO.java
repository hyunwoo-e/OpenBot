/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.conversation.dialog;

import com.bot.api.config.UserMapper;
import com.bot.api.conversation.Conversable;
import com.bot.api.model.conversation.ConversationResponse;
import com.bot.api.model.nlu.NLUValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ProfileBO extends Conversable {

    @Autowired
    private UserMapper userMapper;
    public ConversationResponse response(String userKey) throws Exception{
        if(userMapper.get(userKey).getSub() == null) {
            userMapper.get(userKey).setSub("$프로필조회");
        }

        if(userMapper.get(userKey).getSub().equals("$프로필조회")) {
            return getProfile(userKey);
        }

        throw new Exception();
    }

    public ConversationResponse getProfile(String userKey) {

        // Define SLOT
        HashMap<String, String> slots = new HashMap<String, String>();
        slots.put("@프로필", "어떤 정보를 제공해 드릴까요?");

        // Request Entity
        ConversationResponse conversationResponse = checkEntities(userKey, slots);
        if(conversationResponse != null) {
            return conversationResponse;
        }

        // Select Profile
        String text = "";
        if (userMapper.get(userKey).getEntityMap().containsKey("@프로필")) {
            for(NLUValue nluValue : userMapper.get(userKey).getEntityMap().get("@프로필")) {
                if(nluValue.getValue().equals("전화번호")) {
                    text += "전화번호는 XXX-XXXX-XXXX ";
                }

                if(nluValue.getValue().equals("주소")) {
                    text += "주소는 XX시 XX구 XX동 XXX ";
                }

                if(nluValue.getValue().equals("메일")) {
                    text += "메일은 XXXXX@XXXX.com ";
                }
            }
            text += "입니다.";
        }

        userMapper.initUser(userKey);
        return ConversationResponse.valueOf(text);
    }
}