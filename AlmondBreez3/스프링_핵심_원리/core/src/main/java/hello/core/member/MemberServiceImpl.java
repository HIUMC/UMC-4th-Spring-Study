package hello.core.member;

public class MemberServiceImpl implements  MemberService{

    //memberRepository에도 의존하고 MemoryMemberRepository(구체화)에도 의존하고 있는 문제가 있음
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
