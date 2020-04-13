package com.deepak.multiplayertutorial.dagger;

import com.deepak.multiplayertutorial.MainActivity;
import dagger.Component;

@TutorialApplicationScope
@Component (modules = {ContextModule.class, StubModule.class, ChannelModule.class })
public interface TutorialApplicationComponent {
    void inject(MainActivity mainActivity);
}
