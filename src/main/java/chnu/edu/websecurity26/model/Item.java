package chnu.edu.websecurity26.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author katia
 * @project web-security
 * @class Item
 * @version 1.0.0
 * @since 27/09/2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private String id;
    private String name;
    private String description;
}