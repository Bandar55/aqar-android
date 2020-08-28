package com.aqar55.utill;

/*
public class Utills {

    public static void replaceFragment (Fragment fragment,int fragmentHolderLayoutId,Context context ){
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(fragmentHolderLayoutId, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public static void attachFragment(int fragmentHolderLayoutId, Fragment fragment, Context context, String tag) {

        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (manager.findFragmentByTag(tag) == null) { // No fragment in backStack with same tag..
            ft.add(fragmentHolderLayoutId, fragment, tag);
            ft.addToBackStack(tag);
            ft.commit();
        } else {
            ft.show((manager.findFragmentByTag(tag))).commit();
            Toast.makeText(context, "hello"+manager.findFragmentByTag(tag).getTag(), Toast.LENGTH_SHORT).show();

        }
    }
}
*/
