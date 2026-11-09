package com.example.material_flow_rate_adjustment.logindata;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotFindComponent {
	public UsernameNotFoundException userNotFind(String name){
		log.info("対象データが見つかりませんでした。ユーザー名: {}", name);
		return throwError();
	}
	
	public UsernameNotFoundException userNotFind(int id){
		log.info("対象データが見つかりませんでした。ユーザーID{}", id);
		return throwError();
	}
	
	UsernameNotFoundException throwError(){
		return new UsernameNotFoundException("アカウントの取り込みに失敗しました");
	}
}