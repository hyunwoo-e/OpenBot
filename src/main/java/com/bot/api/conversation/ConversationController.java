/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.conversation;

import com.bot.api.model.conversation.ConversationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bot")
public class ConversationController {

    @Autowired
    private ConversationBO conversationBO;

    @RequestMapping(value="/message", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity receive(@RequestBody ConversationRequest conversationRequest) throws Exception {
        return new ResponseEntity(conversationBO.response(conversationRequest), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public void globalExceptionHandler(Exception e){
        e.printStackTrace();
    }
}
