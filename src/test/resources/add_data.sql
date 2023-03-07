delete
from workout_info;
delete
from exercise;
delete
from user_workout;

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('seated dumbbell press',
        '1. Sit on a low-back bench and hold a dumbbell in each hand at shoulder level, palms facing forward.
        2. Keeping your head and spine perfectly straight, lift the dumbbells overhead toward one another, stopping just short of having them touch at the top.
        3. Hold the position for a few seconds and then carefully reverse course.
        E.g. https://www.youtube.com/watch?v=lfb3ffbrd4Q',
        'FRONT_DELTS',
        3);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('smith machine seated press',
        '1. Set up for the smith machine shoulder press by setting a bench down in the smith machine and adjusting the back to a 60-85 degree angle.
        2. Now sit down on the bench and adjust the position so that the bar comfortably comes down just in front of your face.
        3. Next, add the weight you want to use and sit down on the bench.
        4. Un-rack the weights and bend your elbows slightly. This is the starting position for the movement.
        5. Slowly lower the weight down until the bar is just below chin height, pause, and then raise the bar back to the starting position without locking your elbows out at the top of the movement.
        E.g. https://www.youtube.com/watch?v=_eBcdPyHRoU',
        'FRONT_DELTS',
        3);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('lateral raise',
        '1. Start in the standing position, keeping your feet shoulder-width apart, your abs tight, your chest up, your head straight, and your shoulders pinched. Hold the dumbbells at either side, retaining a neutral grip.
        2. Now, here comes the hard part. Using just your shoulders and arms, raise the dumbbells a notch above shoulder level.
        3. Hold for a moment.
        4. Lower the dumbbells back to the starting position — with control. Don’t let them just drop to your sides.
        E.g. https://www.youtube.com/watch?v=3VcKaXpzqRo',
        'LATERAL_DELTS',
        6);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('seated lateral raise',
        '1. Sit on a bench and hold a dumbbell in each hand by your side.
        2. Raise both dumbbells to your side until they''re shoulder height.
        3. Lower the dumbbells back to the starting position — with control.
        E.g. https://www.youtube.com/watch?v=YC8tmexqqWY',
        'LATERAL_DELTS',
        6);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('rear delts machine',
        '1. Sit on the machine, facing the pad. You’ll appear to be sitting on it backward, but for this exercise, you’re positioned just right. Adjust the seat height so the handles in front of you are level with your shoulders.
        2. Holding the handles, either with palms down or palms facing each other, press your arms back, keeping them straight or slightly bent.
        3. Squeeze your shoulder blades together, like a set of elevator doors.
        4. Hold here for 2 seconds, then return to the starting position with your hands in front of you. Do this slowly and with control.
        E.g. https://www.youtube.com/watch?v=K-ilBw_D1a4',
        'REAR_DELTS',
        8);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('seated bent-over dumbbell raise',
        '1. Begin by sitting on the end of a bench while holding two dumbbells.
        2. Then bend your torso over and keep your back straight.
        3. Now, raise both dumbbells laterally and out to your sides until your arms are parallel to the floor.
        4. Hold for a couple of seconds while exhaling.
        5. Then lower them back down while inhaling.
        E.g. https://www.youtube.com/watch?v=p1yQnTNE808',
        'REAR_DELTS',
        8);

insert into exercise(exercise_name, description, muscle_group)
values ('machine fly',
        '1. Sit up tall and relax your neck and shoulders.
        2. Grab the handles so that your palms are facing forward.
        3. Press your arms together in front of your chest with a slow, controlled movement. Keep a slight, soft bend in the elbows with wrists relaxed.
        4. Bring your arms slowly back to the starting position, opening your chest and keeping posture strong and upright.
        E.g. https://www.youtube.com/watch?v=wr8OCIugQSU',
        'CHEST');

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('incline barbell press',
        '1. The Incline should be set at 30-45 degrees.
        2. Retract and squeeze your scapulae together when laying on the bench.
        3. Set your grip at a distance that is wider than shoulder grip width.
        4. Begin lowering the bar by TUCKING your elbows at a 45-degree angle.
        5. Touch the bar to your upper chest muscles, just below the clavicles and pause for a fraction of a second.
        E.g. https://www.youtube.com/watch?v=DbFgADa2PL8',
        'CHEST',
        4);

insert into exercise(exercise_name, description, muscle_group)
values ('seated overhead triceps Extension',
        '1. Select the desired weight from the rack and position an adjustable bench at 90 degrees.
        2. To get into position, sit in an upright position and lift the dumbbell to the top of your shoulder. Take a deep breath, overlap your hands around the dumbbell, then press it into position overhead.
        3. Maintain an overlapping grip and slowly lower the dumbbell behind your head by unlocking your elbows.
        4. Once your forearms reach parallel or just below, drive the dumbbell back to the starting point by extending the elbows and flexing the triceps.
        E.g. https://www.youtube.com/watch?v=YbX7Wd8jQ-Q',
        'TRICEPS');

insert into exercise(exercise_name, description, muscle_group)
values ('rope push-down',
        '1. At a cable machine with a rope attachment, hold the rope near the knotted ends and begin the exercise with the elbows bent at about 90 degrees. Your elbows should be next to your torso.
        2. Extend the arms, taking the hands down towards the floor, spreading the rope slightly out on either side as you contract the triceps.
        E.g. https://www.youtube.com/watch?v=vB5OHsJ3EME',
        'TRICEPS');

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('bench press',
        '1. Lie on the bench with your eyes under the bar.
        2. Grab the bar with a medium grip-width.
        3. Unrack the bar by straightening your arms.
        4. Lower the bar to your mid-chest.
        5. Press the bar back up until your arms are straight.
        E.g https://www.youtube.com/watch?v=-MAABwVKxok',
        'CHEST_TRICEPS',
        2);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('dips',
        '1. Grab the parallel bars and jump up, straighten your arms.
        2. Lower your body by bending your arms while leaning forward.
        3. Dip down until your shoulders are below your elbows.
        4. Lift your body up by straightening your arms.
        E.g. https://www.youtube.com/watch?v=wjUmnZH528Y',
        'CHEST_TRICEPS',
        2);

insert into exercise(exercise_name, description, muscle_group)
values ('t-bar row',
        '1. Place the end of an empty barbell into the corner of a room.
        2. Rest a heavy dumbbell or some weight plates on it to hold it down.
        3. Load the opposite end of the bar with plates and straddle it.
        4. Bend over at the hips until your torso is about a 45-degree angle to the floor with arms extended.
        5. Hook a V-grip handle (the kind you see at a cable station) under the bar and hold with both hands.
        6. Keeping your lower back in its natural arch, squeeze your shoulder blades together and pull the bar until the plates touch your chest.
        E.g. https://www.youtube.com/watch?v=KDEl3AmZbVE&t',
        'TRAPS');

insert into exercise(exercise_name, description, muscle_group)
values ('barbell row',
        '1. Stand with your mid-foot under the bar (medium stance)
        2. Bend over and grab the bar (palms down, medium-grip)
        3. Unlock your knees while keeping your hips high
        4. Lift your chest and straighten your back
        5. Pull the bar against your lower chest
        E.g. https://www.youtube.com/watch?v=ML1L5ytxLMY&t',
        'LATS');

insert into exercise(exercise_name, description, muscle_group)
values ('back extension',
        '1. Adjust a back extension bench and get into position. Hold a weight plate against your chest or a barbell across your shoulders if you want to use additional weight.
        2. Lean forward as far as you can by hinging in your hips.
        3. Reverse the movement with control and return to the starting position.
        E.g. https://www.youtube.com/watch?v=Tb9yp81fI9s',
        'LOWER_BACK');

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('barbell curl',
        '1. Grasp a barbell or Olympic bar at around shoulder width apart using an underhand grip.
        2. Stand straight up, feet together, back straight, and with your arms fully extended.
        3. The bar should not be touching your body.
        4. Keeping your eyes facing forwards, elbows tucked in at your sides, and your body completely still, slowly curl the bar up.
        5. Squeeze your biceps hard at the top of the movement, and then slowly lower it back to the starting position.
        E.g. https://www.youtube.com/watch?v=dDI8ClxRS04&t',
        'BICEPS',
        1);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('dumbbell curl',
        '1. Grasp dumbbells parallel to your feet.
        2. Stand straight up, feet together, back straight, and with your arms fully extended.
        3. unwrap the dumbbells so that your fingers are in front of your chest while curling.
        E.g. https://www.youtube.com/watch?v=XE_pHwbst04',
        'BICEPS',
        1);

insert into exercise(exercise_name, description, muscle_group)
values ('barbell wrist curl',
        '1. Sit in the middle of a flat bench with your legs straddling the bench. Hold a barbell with your hands about 15 cm apart, palms facing up.
        2. Bend over at the waist, and place the backs of your forearms against the bench, with your hands and wrists over the edge.
        3. Begin with your wrists extended so that your hands are below your forearms.
        E.g. https://www.youtube.com/watch?v=jv-EBaz8CJE',
        'FOREARMS');

insert into exercise(exercise_name, description, muscle_group)
values ('hanging leg raises',
        '1. Exhale as you lift your feet off the ground, raising your straight legs outward in front of you.
        2. Raise your legs to a level that feels challenging but still enables you to keep good form. Strive to get them parallel to the ground (so that your hips are bent at 90 degrees), or a little higher if you can.
        3. Lower your legs back down slowly until they return to the starting position, inhaling during this portion of the exercise.
        E.g. https://www.youtube.com/watch?v=Pr1ieGZ5atk&t',
        'ABS');

insert into exercise(exercise_name, description, muscle_group)
values ('squats',
        '1. Safely load the barbell onto your traps and shoulders. Stand with your feet shoulder-width apart, toes slightly out, core braced, and chest up.
        2. Initiate a basic squat movement — hips back, knees bent, ensuring they fall out, not in. Pause when your thighs reach about parallel to the ground.
        3. Push through your entire foot to return to start.
        E.g. https://www.youtube.com/watch?v=Dy28eq2PjcM',
        'QUADRICEPS_BUTTOKS');

insert into exercise(exercise_name, description, muscle_group)
values ('lying leg curl machine',
        '1. Exhale and flex your knees, pulling your ankles as close to your buttocks as possible. Keep your hips firmly on the bench.
        2. Hold briefly.
        3. Inhale as you return your feet to the starting position in a slow and controlled movement.
        E.g. https://www.youtube.com/watch?v=1Tq3QdYUuHs&t',
        'HAMSTRINGS');

insert into exercise(exercise_name, description, muscle_group)
values ('seated calf raise machine',
        '1. First you will need to sit down.
        2. After sitting down, you will want to raise the bar to a height where your legs just manage to squeeze underneath the bar.
        3. Third, now that you have the machine set up the way you want it, you can add weights to the sides in order to make it heavier. The load doesn’t have to be completely balanced but we like to keep it balanced just in case.
        4. Sit back down underneath the bar and you are ready to complete your first set of seated calf raises.
        E.g. https://www.youtube.com/watch?v=mTissC9K1FQ',
        'CALVES');

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('incline dumbbell press',
        '1. Lie back on a bench set to a 30-45 degree angle and lift the weights up to shoulder height, palms facing away from you.
        2. Breathe out as you press up with both arms.
        3. Lock out your arms and squeeze your chest before returning slowly to the start position.
        E.g. https://www.youtube.com/watch?v=oOQOgAgJFmU',
        'CHEST',
        4);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('deadlift',
        '1. Stand with your mid-foot under the barbell.
        2. Bend over and grab the bar with a shoulder-width grip.
        3. Bend your knees until your shins touch the bar.
        4. Lift your chest up and straighten your lower back.
        5. Take a big breath, hold it, and stand up with the weight.
        E.g. https://www.youtube.com/watch?v=r4MzxtBKyNE',
        'FULL_BACK',
        5);

insert into exercise(exercise_name, description, muscle_group, alternative_id)
values ('pull-ups',
        '1. Exhale while pulling yourself up so your chin is level with the bar. Pause at the top.
        2. Lower yourself (inhaling as you go down) until your elbows are straight.
        E.g. https://www.youtube.com/watch?v=eGo4IYlbE5g',
        'FULL_BACK',
        5);

insert into exercise(exercise_name, description, muscle_group)
values ('bulgarian squats',
        '1. Stand with your feet shoulder-width apart, about two steps away from a bench, a box, or another knee-high surface.
        2. Rest the top of one foot on the object behind you and step your front foot forward far enough that you can squat without your knee going past your toes.
        3. Leaning slightly forward, lower down until your front thigh is almost parallel to the floor.
        E.g. https://www.youtube.com/watch?v=vLuhN_glFZ8',
        'QUADRICEPS_BUTTOKS');

insert into exercise(exercise_name, description, muscle_group)
values ('leg extension',
        '1. Place your hands on the hand bars.
        2. Lift the weight while exhaling until your legs are almost straight. Do not lock your knees. Keep your back against the backrest and do not arch your back.
        3. Exhale and lower the weight back to starting position.
        E.g. https://www.youtube.com/watch?v=swZQC689o9U',
        'QUADRICEPS');

insert into exercise(exercise_name, description, muscle_group)
values ('hip thrust',
        '1. Start seated on the floor, knees bent, feet slightly wider than hip-distance apart. The upper back should be resting against the edge of the weight bench in the center of the bench.
        2. Place the weight bar across the hips. Hold onto the bar to keep it in place, but do not use your arms to lift it.
        3. Squeeze the glutes and press the bar straight up until the hips are in line with the shoulders and knees.
        4. Slowly lower the bar down until the hips are just a few inches off the floor.
        E.g. https://www.youtube.com/watch?v=Zp26q4BY5HE',
        'BUTTOKS');

insert into exercise(exercise_name, description, muscle_group)
values ('t-bar rows',
        '1. Bend over the end of the bar near to the weight and take it with a V-grip handle in both hands.
        2. Maintaining a neutral arch in your lower back, squeeze your shoulder blades together and lift the bar, so the plate touches your chest.
        3. Slowly lower back down to the starting position, keeping your back and shoulders engaged with the motion.
        E.g. https://www.youtube.com/watch?v=KDEl3AmZbVE',
        'REAR_DELTS_LATS');

insert into exercise(exercise_name, description, muscle_group)
values ('reverse grip dumbbells curl',
        '1. Stand with your legs shoulder-width apart and hold bar at your thighs with an overhand grip.
        2. Raise your forearms, keeping your upper arms close to your body, so that your palms face forward and the bar are shoulder height.
        3. Slowly return the bar and repeat.
        E.g. https://www.youtube.com/watch?v=nRgxYX2Ve9w',
        'BICEPS_FOREARMS');


insert into workout_info(user_id, workout_day, number_per_day, exercise_id, exercise_rep, exercise_set)
values (1, 1, 1, 1, 8, 4);

insert into workout_info(user_id, workout_day, number_per_day, exercise_id, exercise_rep, exercise_set)
values (1, 1, 2, 4, 8, 3);

insert into workout_info(user_id, workout_day, number_per_day, exercise_id, exercise_rep, exercise_set)
values (1, 2, 1, 6, 6, 5);

insert into workout_info(user_id, workout_day, number_per_day, exercise_id, exercise_rep, exercise_set)
values (1, 2, 2, 5, 10, 4);


insert into user_workout(exercise_name, exercise_rep, exercise_set, workout_day, number_per_day,
                         user_id)
values ('bench press', 10, 3, 1, 1, 1);

insert into user_workout(exercise_name, exercise_rep, exercise_set, workout_day, number_per_day,
                         user_id)
values ('deadlift', 7, 4, 1, 2, 1);

insert into user_workout(exercise_name, exercise_rep, exercise_set, workout_day, number_per_day,
                         user_id)
values ('90 degree press', 10, 4, 1, 1, 2);