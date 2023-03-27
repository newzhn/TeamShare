package com.zhn.teamsharebackend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zhn.teamsharebackend.domain.dto.UserDTO;
import lombok.Data;

/**
 * 队伍表
 * @TableName team
 */
@TableName(value ="team")
@Data
public class Team implements Serializable {
    /**
     * 队伍ID
     */
    @TableId(type = IdType.AUTO)
    private Long teamId;

    /**
     * 队伍名
     */
    private String teamName;

    /**
     * 队伍描述
     */
    private String teamDescribe;

    /**
     * 队伍最大人数
     */
    private Integer teamSize;

    /**
     * 队员id集合
     */
    private String memberIds;

    /**
     * 队长id
     */
    private Long captainId;

    /**
     * 队伍状态。。。
     */
    private Integer teamStatus;

    /**
     * 队伍密码，只有在队伍加密状态下有值
     */
    private String teamPassword;

    /**
     * 队伍截止时间
     */
    private Date deadline;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除，0是未删除，1是已删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Team(long teamId, String memberIds) {
        this.teamId = teamId;
        this.memberIds = memberIds;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Team other = (Team) that;
        return (this.getTeamId() == null ? other.getTeamId() == null : this.getTeamId().equals(other.getTeamId()))
            && (this.getTeamName() == null ? other.getTeamName() == null : this.getTeamName().equals(other.getTeamName()))
            && (this.getTeamDescribe() == null ? other.getTeamDescribe() == null : this.getTeamDescribe().equals(other.getTeamDescribe()))
            && (this.getTeamSize() == null ? other.getTeamSize() == null : this.getTeamSize().equals(other.getTeamSize()))
            && (this.getMemberIds() == null ? other.getMemberIds() == null : this.getMemberIds().equals(other.getMemberIds()))
            && (this.getCaptainId() == null ? other.getCaptainId() == null : this.getCaptainId().equals(other.getCaptainId()))
            && (this.getTeamStatus() == null ? other.getTeamStatus() == null : this.getTeamStatus().equals(other.getTeamStatus()))
            && (this.getTeamPassword() == null ? other.getTeamPassword() == null : this.getTeamPassword().equals(other.getTeamPassword()))
            && (this.getDeadline() == null ? other.getDeadline() == null : this.getDeadline().equals(other.getDeadline()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeamId() == null) ? 0 : getTeamId().hashCode());
        result = prime * result + ((getTeamName() == null) ? 0 : getTeamName().hashCode());
        result = prime * result + ((getTeamDescribe() == null) ? 0 : getTeamDescribe().hashCode());
        result = prime * result + ((getTeamSize() == null) ? 0 : getTeamSize().hashCode());
        result = prime * result + ((getMemberIds() == null) ? 0 : getMemberIds().hashCode());
        result = prime * result + ((getCaptainId() == null) ? 0 : getCaptainId().hashCode());
        result = prime * result + ((getTeamStatus() == null) ? 0 : getTeamStatus().hashCode());
        result = prime * result + ((getTeamPassword() == null) ? 0 : getTeamPassword().hashCode());
        result = prime * result + ((getDeadline() == null) ? 0 : getDeadline().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teamId=").append(teamId);
        sb.append(", teamName=").append(teamName);
        sb.append(", teamDescribe=").append(teamDescribe);
        sb.append(", teamSize=").append(teamSize);
        sb.append(", memberIds=").append(memberIds);
        sb.append(", captainId=").append(captainId);
        sb.append(", teamStatus=").append(teamStatus);
        sb.append(", teamPassword=").append(teamPassword);
        sb.append(", deadline=").append(deadline);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}