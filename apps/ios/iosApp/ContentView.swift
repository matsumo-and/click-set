import SwiftUI

struct ContentView: View {
    @State private var bpm: Double = 120
    @State private var isPlaying = false
    @State private var beatOn = false
    @State private var lastBeatTime: Date = .distantPast

    private let ticker = Timer.publish(every: 0.02, on: .main, in: .common).autoconnect()

    var body: some View {
        VStack(spacing: 40) {
            Text("click set")
                .font(.title2)
                .foregroundStyle(.secondary)

            Circle()
                .fill(beatOn ? Color.accentColor : Color(.systemGray5))
                .frame(width: 120, height: 120)
                .animation(.easeOut(duration: 0.08), value: beatOn)

            VStack(spacing: 4) {
                Text("\(Int(bpm))")
                    .font(.system(size: 80, weight: .bold, design: .rounded))
                Text("BPM")
                    .font(.caption)
                    .foregroundStyle(.secondary)
            }

            HStack(spacing: 16) {
                Button("−") { if bpm > 40 { bpm -= 1 } }
                    .buttonStyle(.bordered)
                    .font(.title2)

                Slider(value: $bpm, in: 40...240, step: 1)

                Button("+") { if bpm < 240 { bpm += 1 } }
                    .buttonStyle(.bordered)
                    .font(.title2)
            }
            .padding(.horizontal)

            Button {
                isPlaying.toggle()
                lastBeatTime = .distantPast
            } label: {
                Image(systemName: isPlaying ? "stop.fill" : "play.fill")
                    .font(.system(size: 32))
                    .frame(width: 80, height: 80)
                    .background(isPlaying ? Color.red : Color.accentColor)
                    .foregroundStyle(.white)
                    .clipShape(Circle())
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(.systemBackground))
        .onReceive(ticker) { now in
            guard isPlaying else { return }
            let interval = 60.0 / bpm
            if now.timeIntervalSince(lastBeatTime) >= interval {
                lastBeatTime = now
                beatOn = true
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.08) {
                    beatOn = false
                }
            }
        }
    }
}

#Preview {
    ContentView()
}
