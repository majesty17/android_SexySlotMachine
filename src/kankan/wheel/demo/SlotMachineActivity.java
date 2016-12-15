package kankan.wheel.demo;


import kankan.wheel.demo.R;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SlotMachineActivity extends Activity {
	
    final String string_items[][] = new String[][] {
    		new String[] {"老公               老婆的", "老婆               老公的"},
    		new String[] {"咬", "摸", "揉", "亲", "吸", "吹", "打", "舔" },
    		new String[] {"嘴唇", "脖子", "胸部", "私处", "屁股", "耳朵", "脸蛋"},
    		};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.slot_machine_layout);
        initWheel(R.id.slot_1,0);
        initWheel(R.id.slot_2,1);
        initWheel(R.id.slot_3,2);

        
        Button mix = (Button)findViewById(R.id.btn_mix);
        mix.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mixWheel(R.id.slot_1);
                mixWheel(R.id.slot_2);
                mixWheel(R.id.slot_3);
            }
        });
        
        //updateStatus();
    }
    
    // Wheel scrolled flag
    private boolean wheelScrolled = false;
    
    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            updateStatus();
        }
    };
    
    // Wheel changed listener
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {
                updateStatus();
            }
        }
    };
    
    /**
     * Updates status
     */
    private void updateStatus() {
        TextView text = (TextView) findViewById(R.id.pwd_status);
        text.setText(getStatus());
    }

    private CharSequence getStatus() {
		int i0=getWheel(R.id.slot_1).getCurrentItem();
		int i1=getWheel(R.id.slot_2).getCurrentItem();
		int i2=getWheel(R.id.slot_3).getCurrentItem();
		return string_items[0][i0].substring(0,3) + string_items[1][i1]+
				string_items[0][i0].substring(16,20) + string_items[2][i2] + "!";
	}

	/**
     * Initializes wheel
     * @param id the wheel widget Id
     * @param which which wheel
     */
    private void initWheel(int id,int which) {
        ArrayWheelAdapter<String> adapter =
                new ArrayWheelAdapter<String>(this, string_items[which]);
            adapter.setTextSize(18);
        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem((int)(Math.random() * 10));
        
        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(false);
    }

    /**
     * Returns wheel by Id
     * @param id the wheel Id
     * @return the wheel with passed Id
     */
    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }
    
    /**
     * Mixes wheel
     * @param id the wheel id
     */
    private void mixWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.scroll(750*((Math.random()-0.5)>0?1:-1) + (int)(Math.random() * 150), 
        		3000 + (int)(Math.random()*1000));
    }
    
}
