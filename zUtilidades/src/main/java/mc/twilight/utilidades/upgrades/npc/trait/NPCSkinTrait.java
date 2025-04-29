package mc.twilight.utilidades.upgrades.npc.trait;

import mc.twilight.core.libraries.npclib.api.npc.NPC;
import mc.twilight.core.libraries.npclib.npc.skin.Skin;
import mc.twilight.core.libraries.npclib.npc.skin.SkinnableEntity;
import mc.twilight.core.libraries.npclib.trait.NPCTrait;

public class NPCSkinTrait extends NPCTrait {

    private Skin skin;

    public NPCSkinTrait(NPC npc, String value, String signature) {
        super(npc);
        this.skin = Skin.fromData(value, signature);
    }

    @Override
    public void onSpawn() {
        this.skin.apply((SkinnableEntity) this.getNPC().getEntity());
    }
}
