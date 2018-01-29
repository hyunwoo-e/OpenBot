/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.config;

import com.bot.api.model.nlu.NLUValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@AllArgsConstructor(staticName = "valueOf")
public class Conversation {
    private String dialog;
    private String sub;
    private HashMap<String, ArrayList<NLUValue>> entityMap;
    private Integer tryCount;
}