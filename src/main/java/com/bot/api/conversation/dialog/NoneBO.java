/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.conversation.dialog;

import com.bot.api.conversation.Conversable;
import com.bot.api.config.UserMapper;
import com.bot.api.model.conversation.ConversationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoneBO extends Conversable {

    @Autowired
    private UserMapper userMapper;
    public ConversationResponse response(String userKey) {
        userMapper.initUser(userKey);
        return ConversationResponse.valueOf("죄송합니다. 잘 알아듣지 못했습니다.");
    }
}