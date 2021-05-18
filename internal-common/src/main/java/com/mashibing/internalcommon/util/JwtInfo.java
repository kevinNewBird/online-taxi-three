package com.mashibing.internalcommon.util;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtInfo {
    String subject;
    Long issueDate;
}