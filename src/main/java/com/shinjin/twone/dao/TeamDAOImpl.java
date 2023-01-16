package com.shinjin.twone.dao;

import com.shinjin.twone.dto.MemDTO;
import com.shinjin.twone.dto.TeamDTO;
import com.shinjin.twone.dto.TestDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class TeamDAOImpl implements TeamDAO{


  @Override
  public List<MemDTO> selectTeamList() {
    return selectTeamList();
  }

  @Override
  public int leaderSeq() {
    return leaderSeq();
  }

  @Override
  public int changeAllow(TeamDTO dto) {
    return changeAllow(dto);
  }

  @Override
  public int memberAdd(HashMap<String, Object> map) {
    return memberAdd(map);
  }

//  public int memberAdd(String email){
//    return memberAdd(email);
//  }
}
