package net.lodgames.user.user.model;


import net.lodgames.user.user.constants.LoginType;
import net.lodgames.user.user.constants.Os;
import net.lodgames.user.user.constants.ProviderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAddInfo {
    private LoginType loginType;
    private ProviderType providerType;
    private Os os;
}
