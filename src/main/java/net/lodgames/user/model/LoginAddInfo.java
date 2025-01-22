package net.lodgames.user.model;


import net.lodgames.user.constants.LoginType;
import net.lodgames.user.constants.Os;
import net.lodgames.user.constants.ProviderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAddInfo {
    private LoginType loginType;
    private ProviderType providerType;
    private Os os;
}
