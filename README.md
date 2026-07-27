Currently implemented:
-
- Soul Iota: contains the "true" identity of the entity (Default entity iota is now treated as if it references the "Body")
- Rend Mind Spell: takes an entity iota as input and attempts to remove its soul. This removes mob ai and stops player possession
<img width="234" height="235" alt="image" src="https://github.com/user-attachments/assets/24881bd9-5adb-4afe-94c1-678beb2c74f4" />

- Conjoin Spirit Spell: takes a soul iota and entity iota as input and attempts to place the soul inside of the body. This will not remove souls from bodies or overwrite existing ones, you need Rend Mind for that
  - Currently only accepts player souls
<img width="234" height="236" alt="image" src="https://github.com/user-attachments/assets/50fadd25-eb74-45f9-88b5-60505ecc5bb2" />

- Spirit's Purification: pops an entity iota from the stack and pushes a soul iota of the soul inside that body
<img width="235" height="236" alt="image" src="https://github.com/user-attachments/assets/d0c54448-f67f-421b-b12c-8f459bf4143c" />

- Mind's Purification: pops a soul iota from the stack and pushes an entity iota of the body the soul currently inhabits
<img width="234" height="235" alt="image" src="https://github.com/user-attachments/assets/06a417fa-3031-4337-bc1e-2838c6b5e042" />


Future Plans:
- 
- Make it impossible to cast Rend Mind on a creature wearing a helmet. Yep, that's it. I'm hoping to make it fairly simple to avoid having this spell cast on you.
- Fix an issue where a new Hex Notebook is spawned when a player leaves their body
- Make Conjoin Spirit accept any soul iota. This should allow for the "swapping" of mob ai
- Allow for Rend Mind to "write" a soul to a soul vessel. Entities don't become wanderers, so it be a good option for moving/storing removed souls (maybe a different spell? similar to the trinket/artifact spells)
- Add a spell for creating new player shells, maybe multiple tiers? (these could even act as "backups" for you to possess after death)
  - Armor Stand base
  - Amethyst Shell
  - Player Shell
- Add a unique color/inline identifier for each soul's name on the stack. An... aura?
- Add string parsing for Soul Iotas

UNDECIDED FEATURES
-
- Change some of the flavor/functionality of Flay Mind to fit this mod (since the spells here are basically "sister" spells)
- Make Requiem wanderers (intangable players) untargetable by entity spells, instead needing a special spirit modifier to actions like Scout's Distillation
  - Astral Distillation?
