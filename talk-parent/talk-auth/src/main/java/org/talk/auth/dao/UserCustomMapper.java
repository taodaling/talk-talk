package org.talk.auth.dao;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCustomMapper {
    void orFields(@Param("ids") List<Integer> ids, @Param("fields") Long fields);
    void andFields(@Param("ids") List<Integer> ids, @Param("fields") Long fields);
}
