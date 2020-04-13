package com.deepak.multiplayertutorial.services;


import com.deepak.multiplayertutorial.TutorialActionServiceGrpc;
import com.deepak.multiplayertutorial.actions.GetScore;

import io.grpc.stub.StreamObserver;

import static com.deepak.multiplayertutorial.utils.Constants.MAX_PLAYERS;

public class TutorialActionServiceImpl extends TutorialActionServiceGrpc.TutorialActionServiceImplBase {

    @Override
    public void getScore(GetScore.GetScoreRequest request, StreamObserver<GetScore.GetScoreResponse> responseObserver) {

        int newId = request.getName().charAt(0) % MAX_PLAYERS;

        System.out.println("Action service called - getScore()");

        responseObserver.onNext(GetScore.GetScoreResponse.newBuilder().setMessage("Hello " + request.getName() + "! Your new ID is " + newId)
                .setStatusCode(GetScore.GetScoreResponse.StatusCode.OK).build());
        responseObserver.onCompleted();
    }
}
