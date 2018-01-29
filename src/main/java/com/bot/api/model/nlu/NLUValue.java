/* *
* Authors: Hyunwoo Lee <hyunwoo9301@naver.com>
* Released under the MIT license.
* */

package com.bot.api.model.nlu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(staticName = "valueOf")
@Data
public class NLUValue implements Serializable {
    private String value;
    private Integer start;
    private Integer end;
}