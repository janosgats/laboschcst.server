package com.laboschqpa.server.api.dto.ugc.riddleeditor;

import com.laboschqpa.server.api.dto.ugc.GetUserGeneratedContentResponse;
import com.laboschqpa.server.entity.usergeneratedcontent.Riddle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetRiddleResponse extends GetUserGeneratedContentResponse {
    private String title;
    private String hint;
    private String solution;

    public GetRiddleResponse() {
        super();
    }

    public GetRiddleResponse(Riddle riddle, boolean includeHint, boolean includeSolution) {
        this(riddle, includeHint, includeSolution, false);
    }

    /**
     * @param includeAttachments Set this to {@code false} if the attachments should not be got
     *                           (e.g. to avoid {@link org.hibernate.LazyInitializationException})!
     */
    public GetRiddleResponse(Riddle riddle, boolean includeHint, boolean includeSolution, boolean includeAttachments) {
        super(riddle, includeAttachments);
        this.title = riddle.getTitle();

        if (includeHint) {
            this.hint = riddle.getHint();
        }
        if (includeSolution) {
            this.solution = riddle.getSolution();
        }
    }
}
