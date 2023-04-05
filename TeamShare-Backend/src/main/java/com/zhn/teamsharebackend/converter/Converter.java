package com.zhn.teamsharebackend.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The interface Converter.
 *
 * @param <T>   the type parameter
 * @param <DTO> the type parameter
 * @param <VO>  the type parameter
 * @author zhn
 * @version 1.0
 */
public interface Converter<T,DTO,VO> {
    /**
     * Do to dto dto.
     *
     * @param t the t
     * @return the dto
     */
    DTO doToDto(T t);

    /**
     * Dto to vo vo.
     *
     * @param dto the dto
     * @return the vo
     */
    VO dtoToVo(DTO dto);

    /**
     * Vo to dto dto.
     *
     * @param vo the vo
     * @return the dto
     */
    DTO voToDto(VO vo);

    /**
     * Dto to do t.
     *
     * @param dto the dto
     * @return the t
     */
    T dtoToDo(DTO dto);

    /**
     * Do to dto list list.
     *
     * @param t the t
     * @return the list
     */
    default List<DTO> doToDtoList(List<T> t) {
        if (t == null || t.size() == 0) {
            return null;
        }
        return t.stream().map(this::doToDto).collect(Collectors.toList());
    }

    /**
     * Dto to vo list list.
     *
     * @param dto the dto
     * @return the list
     */
    default List<VO> dtoToVoList(List<DTO> dto) {
        if (dto == null || dto.size() == 0) {
            return null;
        }
        return dto.stream().map(this::dtoToVo).collect(Collectors.toList());
    }

    /**
     * Vo to dto list list.
     *
     * @param vo the vo
     * @return the list
     */
    default List<DTO> voToDtoList(List<VO> vo) {
        if (vo == null || vo.size() == 0) {
            return null;
        }
        return vo.stream().map(this::voToDto).collect(Collectors.toList());
    }

    /**
     * Dto to do list list.
     *
     * @param dto the dto
     * @return the list
     */
    default List<T> dtoToDoList(List<DTO> dto) {
        if (dto == null || dto.size() == 0) {
            return null;
        }
        return dto.stream().map(this::dtoToDo).collect(Collectors.toList());
    }
}
