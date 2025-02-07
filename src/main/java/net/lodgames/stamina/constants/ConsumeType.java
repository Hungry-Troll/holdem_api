package net.lodgames.stamina.constants;

import lombok.Getter;

@Getter
public enum ConsumeType {
    TUTORIAL(0, 10),        //튜토리얼
    PRACTICE(1,1),        //연습
    INFINITE_DEFENSE(2, 20),//무한디펜스
    BATTLE_ROYALE(3, 10);   //배틀로얄

    final int status;
    final int consumeValue;

    ConsumeType(int status, int consumeValue){
        this.status = status;
        this.consumeValue = consumeValue;
    }
}
